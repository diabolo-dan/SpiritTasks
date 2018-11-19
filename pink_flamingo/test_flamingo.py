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


class TestFibonnaci(unittest.TestCase):
    def test_is_fib(self):
        fibnnacci_numbers = [1,2,3,5,8,13,21]
        for n in fibnnacci_numbers:
            self.assertTrue(pink_flamingo.is_fib(n))

    def test_non_fib(self):
        non_fibonacci_numbers = [4,6,7,9,10,11,12,14,20,107,20000]
        for n in non_fibonacci_numbers:
            self.assertFalse(pink_flamingo.is_fib(n))
