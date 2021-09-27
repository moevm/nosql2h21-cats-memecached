package info.moevm.se.nosqlcatsmemecached;

import info.moevm.se.nosqlcatsmemecached.repository.CatMemcachedRepository;
import info.moevm.se.nosqlcatsmemecached.repository.HelloWorldRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NosqlCatsMemecachedApplicationTests {

	@Autowired
	HelloWorldRepository repository;

	@Test
	void memcachedIsWorkingAsItNecessaryInHelloWorldExample() {
		repository.set("aba", "caba");
		assert repository.get("aba").equals("caba") : "key 'aba' should return 'caba'";

		repository.set("hello", "world");
		repository.set("all_cats", "are beautiful");

		assert repository.get("hello").equals("world") : "key 'hello' should return 'world'";
		assert repository.get("all_cats").equals("are beautiful") : "key 'all_cats' should return 'are beautiful'";

	}

}
