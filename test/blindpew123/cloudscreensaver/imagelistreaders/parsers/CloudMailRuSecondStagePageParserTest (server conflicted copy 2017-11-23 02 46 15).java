package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CloudMailRuSecondStagePageParserTest {

/*	Инициализация страницы
	передача страницы с нужным тэгом
		в списке должна быть ссылка
	страницы без тэга
		список пустой
	страница с ошибочным url - возможная ситуация при смене формата
		выбрасываем исключение. проблема для этого ридера нерешаема без перекомпиляции */
	
	PageParser parser;
		
	
	@Before
	public void setUp() throws Exception {
		parser = new CloudMailRuSecondStagePageParser("","https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC05252.jpg");
	}

	@Test
	public void testNormalProcessing() throws MalformedURLException, InterruptedException {
		Map<?,?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(1));
	}

	@Test(expected = MalformedURLException.class)
	public void testWrongURLException() throws MalformedURLException, InterruptedException {
		parser = new CloudMailRuSecondStagePageParser("","");
		Map<?,?> testMap = parser.processPage();
		fail();
	}
	
	@Test
	public void testNonReadablePage() throws MalformedURLException, InterruptedException {
		parser = new CloudMailRuSecondStagePageParser("https://cloud.mail.ru/public/", "DQEv/h");
		Map<?,?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(0));
	}

}
