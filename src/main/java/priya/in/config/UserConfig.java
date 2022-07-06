package priya.in.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class UserConfig {

	@Bean(name = "userThread")
	public Executor taskExcutor() {
		ThreadPoolTaskExecutor excutor = new ThreadPoolTaskExecutor();
		excutor.setCorePoolSize(2);
		excutor.setMaxPoolSize(2);
		excutor.setQueueCapacity(100);
		excutor.setThreadNamePrefix("userThread_");
		excutor.initialize();
		return excutor;
	}
}
