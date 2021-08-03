# teapot
This project doesn't do very much, it's just a teapot.

Start it up from your terminal with `./gradlew run`.

The application can respond to web requests.
```bash
$ curl -I localhost:3128
HTTP/1.1 418 I'm a teapot
Proxy-agent: Teapot/0.1
```

And it can also respond to proxy CONNECTs.
```bash
$ curl -I -x localhost:3128 fakeurl.com
HTTP/1.1 418 I'm a teapot
Proxy-agent: Teapot/0.1
```
