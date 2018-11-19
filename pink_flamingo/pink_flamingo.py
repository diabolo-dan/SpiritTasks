import gmpy

def is_fib(n):
    x = 5 * n**2
    return gmpy.is_square(x + 4) or gmpy.is_square(x - 4)
