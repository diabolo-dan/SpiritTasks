module RomanSiteTest exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)

import Test.Html.Query as Query
import Test.Html.Selector exposing (text, tag)

import Http

import RomanMathsSite exposing (..)

goodGotText: Msg
goodGotText = GotText (Ok "XX")
badGotText: Msg
badGotText = GotText (Err Http.Timeout)

suite : Test
suite =
    describe "Roman Maths Site"
        [ describe "Model"
            [ test "should initialise empty request" <|
                \_ ->
                    init_model.request |>
                    Expect.equal ""
            , test "should initialise response " <|
                \_ ->
                    init_model.response |>
                    Expect.equal "Make a request"
            ]
        , describe "View"
            [ test "should have Submit button" <|
                \_ ->
                    view init_model |>
                    Query.fromHtml |>
                    Query.find [ tag "button" ] |>
                    Query.has [ text "Submit" ]
            ]
        , describe "build url"
            [ test "http request url is correctly built" <|
                \_ ->
                    buildUrl "X + X" |>
                    Expect.equal "http://127.0.0.1:4000/?expr=X%20%2B%20X"
            ]
        , describe "Update"
            [ test "update change model request" <|
                \_ ->
                    update (Change "X + X") init_model |>
                    Tuple.first |>
                    .request |>
                    Expect.equal "X + X"
            , test "update change model response" <|
                \_ ->
                    update (Change "X + X") init_model |>
                    Tuple.first |>
                    .response |>
                    Expect.equal init_model.response
            , test "update change  cmd" <|
                \_ ->
                    update (Change "X + X") init_model |>
                    Tuple.second |>
                    Expect.equal Cmd.none
            , test "update submit model request" <|
                \_ ->
                    update Submit init_model |>
                    Tuple.first |>
                    .request |>
                    Expect.equal init_model.request
            , test "update submit model response" <|
                \_ ->
                    update Submit init_model |>
                    Tuple.first |>
                    .response |>
                    Expect.equal init_model.response
            , test "update submit cmd" <|
                \_ ->
                    update Submit init_model |>
                    Tuple.second |>
                    Expect.notEqual Cmd.none
            , test "update GotText model request" <|
                \_ ->
                    update goodGotText init_model |>
                    Tuple.first |>
                    .request |>
                    Expect.equal init_model.request
            , test "update GotText model response" <|
                \_ ->
                    update goodGotText init_model |>
                    Tuple.first |>
                    .response |>
                    Expect.equal "XX"
            , test "update GotText cmd" <|
                \_ ->
                    update goodGotText init_model |>
                    Tuple.second |>
                    Expect.equal Cmd.none
            , test "update Error model request" <|
                \_ ->
                    update badGotText init_model |>
                    Tuple.first |>
                    .request |>
                    Expect.equal init_model.request
            , test "update Error model response" <|
                \_ ->
                    update badGotText init_model |>
                    Tuple.first |>
                    .response |>
                    Expect.equal "Error: Timeout"
            , test "update Error cmd" <|
                \_ ->
                    update badGotText init_model |>
                    Tuple.second |>
                    Expect.equal Cmd.none
            ]
        ]
