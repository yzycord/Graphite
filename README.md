# Graphite - API
## Please add to this with pull requests.

# How to compile/run

1. Rename `example.application.yml` to `application.yml` and move the file to `src/main/resources/`

3. Open command line in the parent directory.

4. With maven installed OR using linux:
```console
mvn clean install
```
Without maven installed AND NOT using linux:
```console
mvn.cmd clean install
```
File outputs to `target/graphite-{version}.jar`

4. Put your new jar in a folder

5. run with: 
```console
java -Xmx{amount}{G|MB} -jar {graphite file}
```
