package vip.kassadin.httpbin.blade;

import com.blade.mvc.WebContext;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author kassadin
 */
@Path
public class IndexController {

    @GetRoute
    public String index() {
        return "index";
    }

    @GetRoute("ip")
    @JSON
    public Map<String, Object> ip(Request req, Map<String, Object> resp) {
        String origin = req.address();
        resp.put("origin", origin);
        return resp;
    }

    @GetRoute("uuid")
    @JSON
    public Map<String, Object> uuid(Request req, Map<String, Object> resp) {
        resp.put("uuid", UUID.randomUUID().toString());
        return resp;
    }

    @GetRoute("user-agent")
    @JSON
    public Map<String, Object> userAgent(Request req, Map<String, Object> resp) {
        resp.put("user-agent", req.userAgent());
        return resp;
    }

    @GetRoute("headers")
    @JSON
    public Map<String, Object> headers(Request req, Map<String, Object> resp) {
        resp.put("headers", req.headers());
        return resp;
    }

    @GetRoute("get")
    @JSON
    public Map<String, Object> get() {
        return getMap(new String[]{"args", "headers", "origin", "url"});
    }

    @PostRoute("post")
    @JSON
    public Map<String, Object> post(Request req, Map<String, Object> resp) {
        resp.put("headers", req.headers());
        return resp;
    }

    @Route(value = "patch", method = HttpMethod.PATCH)
    @JSON
    public Map<String, Object> patch(Request req, Map<String, Object> resp) {
        resp.put("headers", req.headers());
        return resp;
    }

    @PutRoute("put")
    @JSON
    public Map<String, Object> put(Request req, Map<String, Object> resp) {
        resp.put("headers", req.headers());
        return resp;
    }

    @DeleteRoute("delete")
    @JSON
    public Map<String, Object> delete(Request req, Map<String, Object> resp) {
        resp.put("headers", req.headers());
        return resp;
    }

    @GetRoute("decode_base64/:value")
    public void  decodeBase64(@PathParam String value, Response response) {
        Objects.requireNonNull(value);
        String origin = new String(Base64.getDecoder().decode(value));
        response.text(origin);
    }

    @GetRoute("encoding")
    public String  encoding( ) {
        return "UTF-8-demo";
    }

    @GetRoute("gzip")
    public void gzip(Response response) {
        Map<String, Object> extras = new HashMap<>();
        extras.put("gzipped", true);
        // todo webhook not support to get result from route
//        return getMap(new String[]{"headers", "origin", "method"}, extras);
        response.text("Not implemented");
    }

    @GetRoute("deflate")
    public void deflate(Response response) {
        response.text("Not implemented");
    }


    @GetRoute("brotli")
    public void brotli(Response response) {
        response.text("Not implemented");
    }

    @GetRoute("status/:status")
    public void status(@PathParam(defaultValue = "200") int status ,Response response) {
        response.status(status);
    }

    @GetRoute("response-headers")
    @JSON
    public Map<String, List<String>> responseHeaders(Request request, Response response) {
        Map<String, List<String>> parameters = request.parameters();
        for (String key : parameters.keySet()) {

            List<String> strings = parameters.get(key);
            if (strings == null || strings.isEmpty()) {
                continue;
            }

            String value = strings.size() == 1 ? strings.get(0) : String.join(",", strings);
            response.header(key, value);
        }

        return parameters;
    }






    private Map<String, Object> getMap(String[] keys) {
        return getMap(keys, null);
    }

    private Map<String, Object> getMap(String[] keys, @Nullable Map<String, Object> extras) {
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

    private String getUrl(Request request) {
        String scheme = request.isSecure() ? "https" : "http";
        String host = request.host();
        String path = request.url();
        return String.format("%s://%s/%s", scheme, host, path);
    }

    private Map<String, Object> semiflatten(Map<String, List<String>> params) {
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
}
