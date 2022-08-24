package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // Configuration은 제외하고 등록
        // Configuration 도 코드를 살펴보면 @Component에 속해있음
        // 우리가 이미 여기저기 만들어둔 예제코드를 (AppConfig 등) 유지하면서, 등록하진 않기 위해
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),
        // 컴포넌트 스캔 범위 지정
        basePackages = {"hello.core.member"},
        // 지정한 클래스의 패키지를 스캔범위로 지정
        basePackageClasses = AutoAppConfig.class

        // 범위를 지정하지 않으면 이 파일이 포함된 패키지 하위 컴포넌트들을 스캔함
)
public class AutoAppConfig {

}
