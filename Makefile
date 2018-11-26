
build-roman:
	docker build -t roman_maths .

run-roman: build-roman
	docker run -p 4000:4000 roman_maths sbt run

