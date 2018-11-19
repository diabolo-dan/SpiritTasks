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

def flamingo(n):
    fizz_buzz_output = fizz_buzz(n)
    if is_fib(n):
        if fizz_buzz_output == 'fizzbuzz':
            return 'pink flamingo'
        else:
            return 'flamingo'
    else:
        return fizz_buzz_output


def main():
    for i in range(1, 100):
        print(flamingo(i))

if __name__ == '__main__':
    main()
