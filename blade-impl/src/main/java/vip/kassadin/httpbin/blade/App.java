package vip.kassadin.httpbin.blade;

import com.blade.Blade;

/**
 *
 *
 */
public class App {
    public static void main(String[] args) {
        Blade.me()
                .listen(8080)
                .start(App.class, args);
    }
}
