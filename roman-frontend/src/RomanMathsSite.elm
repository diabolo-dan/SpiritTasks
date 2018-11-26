module RomanMathsSite exposing (..)
import Html exposing (Html, Attribute, div, input, text, button)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)

import Http

-- main =
  -- Html.beginnerProgram { model = model, view = view, update = update }


-- MODEL

type alias Model =
  { request : String
  , response : String
  }

model : Model
model =
  { request = ""
  , response = "Make a request"
  }


-- UPDATE

-- getRomanMaths : String -> Cmd Msg
-- getRomanMaths request =
--     Http.get
--         { url = request
--         , expect = Http.expectString GotText
--         }
-- 
-- 
-- buildUrl : String -> String
-- buildUrl request =
--     Http.url "127.0.0.1:4000" [("expr", request)]
-- 
type Msg =
  GotText (Result Http.Error String) | Change String | Submit
-- 
-- update : Msg -> Model -> Model
-- update msg model =
--   update_anagrams <|
--   case msg of
--     Change new_word ->
--       { model | word = new_word}
--     AddWord word ->
--       { model | word_list = word :: model.word_list }
-- 

-- VIEW

view : Model -> Html Msg
view model =
  div []
    [ input [ placeholder "Type equation here.", onInput Change ] []
    , button [ onClick Submit ] [ text "Submit" ]
    , div [] [ text model.response ]
    ]
