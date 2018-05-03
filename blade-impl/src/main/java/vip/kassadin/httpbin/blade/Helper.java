package vip.kassadin.httpbin.blade;

import com.blade.mvc.WebContext;
import com.blade.mvc.http.Request;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kassadin
 */
public class Helper {
    public static Map<String, Object> getMap(String[] keys) {
        return getMap(keys, null);
    }

    public static Map<String, Object> getMap(String[] keys, @Nullable Map<String, Object> extras) {
        String[] _keys = {"url", "args", "form", "data", "origin", "headers", "files", "json", "method"};

        Preconditions.checkArgument(Arrays.asList(_keys).containsAll(Arrays.asList(keys)));

        Request request = WebContext.request();

        Map<String, Object> _map = new HashMap<>();
        _map.put("url", getUrl(request));
        _map.put("args", semiflatten(request.parameters()));
        _map.put("form", request.parameters());
        _map.put("data", request.parameters());
        _map.put("origin", request.address());
        _map.put("headers", request.headers());
        _map.put("json", request.parameters());
        _map.put("method", request.method());

        Map<String, Object> outMap = new HashMap<>();
        for (String key : keys) {
            outMap.put(key, _map.get(key));
        }

        if (extras != null && extras.size() > 0) {
            outMap.putAll(extras);
        }

        return outMap;
    }

    public static Map<String, Object> semiflatten(Map<String, List<String>> params) {
        Map<String, Object> outMpas = new HashMap<>();

        List<String> value;
        for (String key : params.keySet()) {
            value = params.get(key);
            if (value.size() == 1) {
                outMpas.put(key, value.get(0));
            } else {
                outMpas.put(key, value);
            }
        }

        return outMpas;
    }

    public static String getUrl(Request request) {
        String scheme = request.isSecure() ? "https" : "http";
        String host = request.host();
        String path = request.url();
        return String.format("%s://%s/%s", scheme, host, path);
    }
}
