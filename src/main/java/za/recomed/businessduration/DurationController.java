package za.recomed.businessduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class DurationController {
	private Logger logger = LoggerFactory.getLogger(DurationController.class);

	@Autowired
	private DurationService durationService;

	@GetMapping(path="/duration", produces=MediaType.TEXT_PLAIN_VALUE)
	public String duration(@RequestParam String start_time, @RequestParam String end_time) {
		logger.debug(new StringBuilder("start_time=").append(start_time).
				append("end_time").append(end_time).toString());
		return durationService.calculate(start_time, end_time).toString();
	}
}