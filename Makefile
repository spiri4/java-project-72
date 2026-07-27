.PHONY: build run shadowJar

build:
	cd app && ./gradlew build

run:
	cd app && ./gradlew run

shadowJar:
	cd app && ./gradlew shadowJar
