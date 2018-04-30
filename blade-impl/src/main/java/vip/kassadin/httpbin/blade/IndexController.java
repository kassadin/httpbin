package vip.kassadin.httpbin.blade;

import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;

import java.util.Map;
import java.util.UUID;

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
    public Map<String, Object> get(Request req, Map<String, Object> resp) {
        resp.put("headers", req.headers());
        return resp;
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


}
