package recomed.sbu.businessduration;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class DurationController {

	@GetMapping(path="/duration", produces=MediaType.TEXT_PLAIN_VALUE)
	public String duration(@RequestParam String start_time, @RequestParam String end_time) {
		return Duration.calculate(start_time, end_time).toString();
	}
}