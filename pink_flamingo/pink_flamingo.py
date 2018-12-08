#! /usr/bin/python3
import gmpy
from typing import List, Callable, Tuple

Rule = Tuple[Callable[[int], bool], str]

def generic_fizz_buzz(rules: List[Rule], n: int) -> str:
    """
    For each rule, if it applies return the appropriate string.

    E.g. for fizzbuzz rules are:
        x % 15 == 0 -> 'fizzbuzz'
        x % 3 == 0  -> 'fizz',
        x % 5 == 0  -> 'buzz',
    """

    for condition, output in rules:
        if condition(n):
            return  output
    else:
        return str(n)


def is_fib(n: int) -> bool:
    """Definition as given in task"""
    x = 5 * n**2
    return gmpy.is_square(x + 4) or gmpy.is_square(x - 4)


FIZZ_BUZZ_RULES: List[Rule] = [
        (lambda x: x % 15 == 0, 'fizzbuzz'),
        (lambda x: x % 3 == 0,  'fizz'),
        (lambda x: x % 5 == 0,  'buzz'),
]

FLAMINGO_RULES: List[Rule] = [
    (lambda x: x % 15 == 0 and is_fib(x), 'pink flamingo'),
    (is_fib, 'flamingo')
] + FIZZ_BUZZ_RULES

fizz_buzz = lambda n: generic_fizz_buzz(FIZZ_BUZZ_RULES, n)
flamingo = lambda n: generic_fizz_buzz(FLAMINGO_RULES, n)

def main():
    for i in range(1, 101):
        print(flamingo(i))

if __name__ == '__main__':
    main()
