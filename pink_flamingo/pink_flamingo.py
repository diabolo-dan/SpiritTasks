#! /usr/bin/python3
import gmpy

class GenericFizzBuzz(object):
    """Takes a list of rules from conditions (as a boolean function on a number) to desired output string.  Returns a callable which, when supplied an integer n, will return the output string determined by the given rules.  An earlier rule will take precedence over an later rule.  If no rule matches, the input n will be returned as a string.

    E.g. for fizzbuzz rules are:
        x % 3 == 0  -> 'fizz',
        x % 5 == 0  -> 'buzz',
        x % 15 == 0 -> 'fizzbuzz'
    """

    def __init__(self, rules):
        self.rules = rules

    def __call__(self, n):
        for condition, output in self.rules:
            if condition(n):
                return  output
        else:
            return str(n)


FIZZ_BUZZ_RULES = [
        (lambda x: x % 15 == 0, 'fizzbuzz'),
        (lambda x: x % 3 == 0,  'fizz'),
        (lambda x: x % 5 == 0,  'buzz'),
]
fizz_buzz = GenericFizzBuzz(FIZZ_BUZZ_RULES)

def is_fib(n):
    """Definition as given in task"""
    x = 5 * n**2
    return gmpy.is_square(x + 4) or gmpy.is_square(x - 4)

FLAMINGO_RULES = [
    (lambda x: x % 15 == 0 and is_fib(x), 'pink flamingo'),
    (is_fib, 'flamingo')
] + FIZZ_BUZZ_RULES

flamingo = GenericFizzBuzz(FLAMINGO_RULES)

def main():
    for i in range(1, 101):
        print(flamingo(i))

if __name__ == '__main__':
    main()
