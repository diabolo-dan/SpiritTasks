#! /usr/bin/python3
import gmpy

class GenericFizzBuzz(object):
    """Takes a list of conditions, as a dictionary with a name index, and a list of rules from sets of those conditions (by name) to desired output string.  Returns a callable which, when supplied an integer n, will return the output string determined by the given rules.  A later rule will take precedence over an earlier rule.  If no rule matches, the input n will be returned as a string.

    E.g. for fizzbuzz conditions are
        fizz: n%3 == 0
        buzz: n%5 == 0
    rules are
        fizz -> 'fizz',
        buzz -> 'buzz',
        fizz + buzz -> 'fizzbuzz'
    """

    def __init__(self, conditions, rules):
        self.conditions = conditions
        self.rules = rules

    def __call__(self, n):
        matching_conditions = {name: condition(n) for name, condition in self.conditions.items()}
        for condition_names, output in reversed(self.rules):
            if all([matching_conditions[i] for i in condition_names]):
                return  output
        else:
            return str(n)


FIZZ_BUZZ_CONDITIONS = {
        'fizz': lambda x: x % 3 == 0,
        'buzz': lambda x: x % 5 == 0,
}
FIZZ_BUZZ_RULES = [
    ({'fizz'}, 'fizz'),
    ({'buzz'}, 'buzz'),
    ({'fizz', 'buzz'}, 'fizzbuzz'),
]
fizz_buzz = GenericFizzBuzz(FIZZ_BUZZ_CONDITIONS, FIZZ_BUZZ_RULES)

def is_fib(n):
    """Definition as given in task"""
    x = 5 * n**2
    return gmpy.is_square(x + 4) or gmpy.is_square(x - 4)

FLAMINGO_CONDITIONS = {'flamingo': lambda x: is_fib(x), **FIZZ_BUZZ_CONDITIONS}
FLAMINGO_RULES = FIZZ_BUZZ_RULES + [
    ({'flamingo'}, 'flamingo'),
    ({'fizz', 'buzz', 'flamingo'}, 'pink flamingo')
]
flamingo = GenericFizzBuzz(FLAMINGO_CONDITIONS, FLAMINGO_RULES)

def main():
    for i in range(1, 101):
        print(flamingo(i))

if __name__ == '__main__':
    main()
