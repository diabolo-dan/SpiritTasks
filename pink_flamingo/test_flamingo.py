#! /usr/local/bin/python3.6m

import unittest
import pink_flamingo

class TestFizzBuzz(unittest.TestCase):
    def test_fizz(self):
        fizzes = [i * 3 for i in range(10) if i%5 != 0]
        for fizz in fizzes:
            self.assertEqual("fizz", pink_flamingo.fizz_buzz(fizz), fizz)

    def test_buzzes(self):
        buzzes = [i * 5 for i in range(10) if i%3 != 0]
        for buzz in buzzes:
            self.assertEqual("buzz", pink_flamingo.fizz_buzz(buzz), buzz)

    def test_fizz_buzz(self):
        fizz_buzzes = [i * 5 *3 for i in range(5)]
        for fizz_buzz in fizz_buzzes:
            self.assertEqual("fizzbuzz", pink_flamingo.fizz_buzz(fizz_buzz), fizz_buzz)

    def test_normal_number(self):
        normal_numbers = [i for i in range(100) if i % 5 != 0 and i % 3 != 0]
        for number in normal_numbers:
            self.assertEqual(str(number), pink_flamingo.fizz_buzz(number), number)


FIBONNACCI_NUMBERS = [1,2,3,5,8,13,21]
NON_FIBONACCI_NUMBERS = [4,6,7,9,10,11,12,14,20,107,20000]
class TestFibonnaci(unittest.TestCase):
    def test_is_fib(self):
        for n in FIBONNACCI_NUMBERS:
            self.assertTrue(pink_flamingo.is_fib(n))

    def test_non_fib(self):
        for n in NON_FIBONACCI_NUMBERS:
            self.assertFalse(pink_flamingo.is_fib(n))


class TestFlamingo(unittest.TestCase):
    def test_non_fibonnaci(self):
        for n in NON_FIBONACCI_NUMBERS:
            self.assertEqual(
                pink_flamingo.fizz_buzz(n),
                pink_flamingo.flamingo(n),
                n
            )
