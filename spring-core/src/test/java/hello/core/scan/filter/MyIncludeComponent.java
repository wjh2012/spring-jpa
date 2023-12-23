package hello.core.scan.filter;

import java.lang.annotation.*;

// 이 어노테이션이 붙은 것은 컴포넌트 스캔에 추가
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
