module RomanSiteTest exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)

import Test.Html.Query as Query
import Test.Html.Selector exposing (text, tag)

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
        , describe "View"
            [ test "should have Submit button" <|
                \_ ->
                    view model |>
                    Query.fromHtml |>
                    Query.find [ tag "button" ] |>
                    Query.has [ text "Submit" ]
            ]
        ]
