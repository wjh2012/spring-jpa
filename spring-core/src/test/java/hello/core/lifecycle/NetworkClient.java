package hello.core.lifecycle;


//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient /*implements InitializingBean, DisposableBean */{

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect(){
        System.out.println("connect = " + url);

    }

    public void call(String message) {
        System.out.println("call = " + url + " message = " + message);

    }

    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close : " + url);
    }

    // 인터페이스 방식
/*
    //InitializingBean
    //의존관계 주입 후
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }
    //DisposableBean
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
*/

    // 메서드 방식 or 어노테이션
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }
    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
