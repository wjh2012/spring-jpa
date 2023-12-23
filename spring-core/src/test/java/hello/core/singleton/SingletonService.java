package hello.core.singleton;

public class SingletonService {

    // 자기 자신을 내부에 static으로 생성
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    // 외부 객체 생성을 막아버리기기
   private SingletonService(){
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }

}
