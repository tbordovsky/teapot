# teapot
This project doesn't do very much, it's just a teapot.

Start it up from your terminal with `./gradlew run`.

The application can respond to web requests.
```
$ curl -I localhost:3128
HTTP/1.1 418 I'm a teapot
Proxy-agent: Teapot/0.1
```

And it can also respond to proxy CONNECTs.
```
$ curl -I -x localhost:3128 example.com
HTTP/1.1 418 I'm a teapot
Proxy-agent: Teapot/0.1
```
