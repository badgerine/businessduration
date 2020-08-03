package za.recomed.businessduration;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

public class DurationControllerTest {

	public String duration(@RequestParam String start_time, @RequestParam String end_time) {
		return null;
	}
}