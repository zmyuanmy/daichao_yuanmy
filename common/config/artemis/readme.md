##Artemis web management config

1. Download HawtIO [here](http://hawt.io/getstarted/index.html)
3. Download HawtIO plugin [here](https://github.com/rh-messaging/artemis-hawtio)
4. Place `hawtio-default-***.war` in $artemis/web/ directory
5. Place `artemis-plugin-***.war` in $artemis/web/ directory
6. Modify `bootstrap.xml` to enable internal Jetty and add plugins, so it contains lines:
```
<web bind="http://localhost:8161" path="web">
   <app url="jolokia" war="jolokia-war-1.3.5.war"/>
   <app url="hawtio" war="hawtio-default-1.5.0.war"/>
   <app url="artemis-plugin" war="artemis-plugin-1.0.0.ER9.war"/>
</web>
```

6. Add arguments to ``JAVA_ARGS`` at `artemis.profile`: 
```
-Dhawtio.realm=activemq -Dhawtio.role=<PPC_ROLE> -Dhawtio.rolePrincipalClasses=org.apache.activemq.artemis.spi.core.security.jaas.RolePrincipal
```
8. Restart broker.

######Results 
- jetty is available at http://localhost:8161
- Raw joloqia is available at http://localhost:8161/joloqia
- HawtIO UI is available at http://localhost:8161/hawtio
