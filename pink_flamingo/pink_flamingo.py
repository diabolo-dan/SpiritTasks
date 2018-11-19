import gmpy

def is_fib(n):
    x = 5 * n**2
    return gmpy.is_square(x + 4) or gmpy.is_square(x - 4)

def fizz_buzz(n):
    if n%3 + n%5 == 0:
        return "fizzbuzz"
    elif n % 3 == 0:
        return "fizz"
    elif n % 5 == 0:
        return "buzz"
    return str(n)

def flamingo_old(n):
    fizz_buzz_output = fizz_buzz(n)
    if is_fib(n):
        if fizz_buzz_output == 'fizzbuzz':
            return 'pink flamingo'
        else:
            return 'flamingo'
    else:
        return fizz_buzz_output

CONDITIONS = [
    lambda x: x % 3 == 0,
    lambda x: x % 5 == 0,
    lambda x: is_fib(x)
]
RULES = [
    ([0], 'fizz'),
    ([1], 'buzz'),
    ([0,1], 'fizzbuzz'),
    ([2], 'flamingo'),
    ([0, 1, 2], 'pink flamingo')
]

def flamingo(n):
    matching_conditions = [condition(n) for condition in conditions]
    for condition_indexes, output in reversed(rules):
        if all([matching_conditions[i] for i in condition_indexes]):
            return  output
    else:
        return str(n)

def main():
    for i in range(1, 100):
        print(flamingo(i))

if __name__ == '__main__':
    main()
