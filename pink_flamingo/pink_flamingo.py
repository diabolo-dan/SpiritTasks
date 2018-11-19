import gmpy

def is_fib(n):
    x = 5 * n**2
    return gmpy.is_square(x + 4) or gmpy.is_square(x - 4)

FIZZ_BUZZ_CONDITIONS = [
    lambda x: x % 3 == 0,
    lambda x: x % 5 == 0,
]
FLAMINGO_CONDITIONS = FIZZ_BUZZ_CONDITIONS + [lambda x: is_fib(x)]
FIZZ_BUZZ_RULES = [
    ([0], 'fizz'),
    ([1], 'buzz'),
    ([0,1], 'fizzbuzz'),
]
FLAMINGO_RULES = FIZZ_BUZZ_RULES + [
    ([2], 'flamingo'),
    ([0, 1, 2], 'pink flamingo')
]

class GenericFizzBuzz(object):
    def __init__(self, conditions, rules):
        self.conditions = conditions
        self.rules = rules

    def __call__(self, n):
        matching_conditions = [condition(n) for condition in self.conditions]
        for condition_indexes, output in reversed(self.rules):
            if all([matching_conditions[i] for i in condition_indexes]):
                return  output
        else:
            return str(n)


fizz_buzz = GenericFizzBuzz(FIZZ_BUZZ_CONDITIONS, FIZZ_BUZZ_RULES)
flamingo = GenericFizzBuzz(FLAMINGO_CONDITIONS, FLAMINGO_RULES)

def main():
    for i in range(1, 100):
        print(flamingo(i))

if __name__ == '__main__':
    main()
