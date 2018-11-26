module RomanMathsSite exposing (..)
import Html exposing (Html, Attribute, div, input, text, button)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)

import Http
import Url.Builder


-- main =
  -- Html.beginnerProgram { model = init_model, view = view, update = update }


-- MODEL

type alias Model =
  { request : String
  , response : String
  }

init_model : Model
init_model =
  { request = ""
  , response = "Make a request"
  }


-- UPDATE

getRomanMaths : String -> Cmd Msg
getRomanMaths request =
    Http.get
        { url = request
        , expect = Http.expectString GotText
        }


buildUrl : String -> String
buildUrl request =
    Url.Builder.crossOrigin "http://127.0.0.1:4000" [] [Url.Builder.string "expr" request]

type Msg =
  GotText (Result Http.Error String) | Change String | Submit

update : Msg -> Model -> Model
update msg model = model

-- VIEW

view : Model -> Html Msg
view model =
  div []
    [ input [ placeholder "Type equation here.", onInput Change ] []
    , button [ onClick Submit ] [ text "Submit" ]
    , div [] [ text model.response ]
    ]
