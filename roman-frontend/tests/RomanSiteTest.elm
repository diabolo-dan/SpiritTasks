module RomanSiteTest exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)

import RomanMathsSite exposing (..)

suite : Test
suite =
    describe "Roman Maths Site"
        [ describe "Model"
            [ test "should initialise empty request" <|
                \_ ->
                    model.request |>
                    Expect.equal ""
            , test "should initialise response " <|
                \_ ->
                    model.response |>
                    Expect.equal "Make a request"
            ]
        ]
