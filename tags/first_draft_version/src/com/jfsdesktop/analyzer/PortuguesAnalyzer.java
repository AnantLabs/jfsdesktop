package com.jfsdesktop.analyzer;

import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * <P>
 * Implementação de Analyzer adaptada para textos em língua portuguesa.
 * 
 * <P>
 * Ignora diferenças entre maiúsculas e minúsculas.
 * <P>
 * Ignora diferenças entre caracteres acentuados e não-acentuados.
 * <P>
 * Elimina palavras muito comuns (stop words) da língua portuguesa.
 * 
 */
public class PortuguesAnalyzer extends Analyzer {

	public static final String[] STOP_WORDS = new String[] { "a", "ao", "aos",
			"as", "cuja", "cujas", "cujo", "cujos", "da", "das", "de", "do",
			"dos", "e", "em", "daquele", "daquela", "daqueles", "daquelas",
			"dela", "dele", "deles", "desta", "deste", "essa", "essas", "esse",
			"esses", "esta", "estas", "este", "estes", "um", "uma", "umas",
			"uns", "com", "lhe", "lhes", "mas", "me", "na", "nas", "nem",
			"nesse", "nesta", "neste", "no", "nos", "no", "o", "os", "para",
			"perante", "pois", "por", "quais", "qual", "que", "quem", "quer",
			"se", "seu", "seus", "sob", "sobre", "sua", "suas", "so", "tal",
			"tanto", "tem", "tendo", "teu", "teus", "toda", "todas", "todo",
			"todos", "tua", "tuas", };

	private Set<String> _stopWords = new HashSet<String>();

	@SuppressWarnings("unchecked")
	public PortuguesAnalyzer() {
		this._stopWords = StopFilter.makeStopSet(STOP_WORDS);
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		// Passa o texto no reader pelo tokenizer default do Lucene,
		// retornando um TokenStream ou seja, uma sequência de tokens.
		//
		// -> separa palavras por espaço em branco (whitespace).
		// -> separa palavras por caracteres de pontuação.
		// -> trata endereços de email e de sites web como um token integral.
		// -> emenda palavras seguidas de pontuação se não houver espaço em
		// seguida.
		TokenStream result = new StandardTokenizer(reader);

		// Passa o tokenstream por um conjunto de filtros, em sequência

		// Filtro padrão
		result = new StandardFilter(result);

		// Converte todas as maiúsculas em minúsculas
		result = new LowerCaseFilter(result);

		// Remove qualquer palavra identificada como "stop word"
		result = new StopFilter(result, _stopWords);

		// Substitui caracteres acentuados e c-cedilha
		// pelos equivalentes sem acentuação.
		result = new PortuguesTokenFilter(result);

		return result;
	}

}

/**
 * <P> Implementação de TokenFilter que substitui caracteres acentuados e c-cedilha
 * pelos equivalentes não-acentuados.
 */
class PortuguesTokenFilter extends TokenFilter {

	private static final String[] CARACTERES_SEM_ACENTO;

	private static final Pattern[] PATTERNS;

	static {
		CARACTERES_SEM_ACENTO = new String[] { "a", "e", "i", "o", "u", "c" };
		PATTERNS = new Pattern[CARACTERES_SEM_ACENTO.length];
		PATTERNS[0] = Pattern.compile("[áàãâä]", Pattern.CASE_INSENSITIVE);
		PATTERNS[1] = Pattern.compile("[éèêë]", Pattern.CASE_INSENSITIVE);
		PATTERNS[2] = Pattern.compile("[íìïî]", Pattern.CASE_INSENSITIVE);
		PATTERNS[3] = Pattern.compile("[óòöõô]", Pattern.CASE_INSENSITIVE);
		PATTERNS[4] = Pattern.compile("[úùüû]", Pattern.CASE_INSENSITIVE);
		PATTERNS[5] = Pattern.compile("ç", Pattern.CASE_INSENSITIVE);
	}

	public PortuguesTokenFilter(TokenStream in) {
		super(in);
	}

	public Token next() throws IOException {

		Token t = input.next();

		if (t == null) {
			return null;
		}

		String termText = replaceSpecial(t.termText());
		Token token = new Token(termText, t.startOffset(), t.endOffset());

		return token;
	}

	private String replaceSpecial(String text) {
		String result = text;
		for (int i = 0; i < PATTERNS.length; i++) {
			Matcher matcher = PATTERNS[i].matcher(result);
			result = matcher.replaceAll(CARACTERES_SEM_ACENTO[i]);
		}
		return result;
	}
}