package vip.kassadin.httpbin.blade;

import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

import java.io.IOException;
import java.util.*;

import static vip.kassadin.httpbin.blade.Helper.*;

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
    public void decodeBase64(@PathParam String value, Response response) {
        Objects.requireNonNull(value);
        String origin = new String(Base64.getDecoder().decode(value));
        response.text(origin);
    }

    @GetRoute("encoding")
    public String encoding() {
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
    public void status(@PathParam(defaultValue = "200") int status, Response response) {
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

    @GetRoute("xml")
    public void xml(Response response) {
        response.contentType("application/xml; charset=UTF-8");
        response.render("sample.xml");
    }

    @GetRoute("image")
    public void image(Response response) throws IOException {
        // todo: check accept
        response.contentType("image/png");
        response.body(Resources.get("images/pig_icon.png"));
    }

    @GetRoute("image_png")
    public void image_png(Response response) throws IOException {
        response.contentType("image/png");
        response.body(Resources.get("images/pig_icon.png"));
    }

    @GetRoute("image_jpeg")
    public void image_jpeg(Response response) throws IOException {
        response.contentType("image/jpeg");
        response.body(Resources.get("images/jackal.jpg"));
    }

    @GetRoute("image_webp")
    public void image_webp(Response response) throws IOException {
        response.contentType("image/webp");
        response.body(Resources.get("images/wolf_1.webp"));
    }

    @GetRoute("image_svg")
    public void image_svg(Response response) throws IOException {
        response.contentType("image/svg+xml");
        response.body(Resources.get("images/svg_logo.svg"));
    }

}
