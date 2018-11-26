module RomanMathsSite exposing (..)
import Html exposing (Html, Attribute, div, input, text, button)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)

import Http
import Url.Builder
import Browser


main =
  Browser.element
    { init = init
    , view = view
    , update = update
    , subscriptions = subscriptions
    }


-- MODEL

type alias Model =
  { request : String
  , response : String
  }

init : () -> (Model, Cmd Msg)
init _ =
    ( init_model
    , Cmd.none
    )

init_model : Model
init_model =
    { request = ""
    , response = "Make a request"
    }

-- UPDATE

getRomanMaths : String -> Cmd Msg
getRomanMaths request =
    Http.get
        { url = buildUrl request
        , expect = Http.expectString GotText
        }


buildUrl : String -> String
buildUrl request =
    Url.Builder.crossOrigin "http://127.0.0.1:4000" [] [Url.Builder.string "expr" request]

type Msg =
  GotText (Result Http.Error String) | Change String | Submit

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        Change new_request ->
            ({ model | request = new_request }, Cmd.none)
        Submit -> (model, getRomanMaths model.request)
        GotText result -> (updateHttpResponse result model, Cmd.none)

updateHttpResponse: Result Http.Error String -> Model -> Model
updateHttpResponse result model =
    case result of
        Ok response -> { model | response = response }
        Err error -> { model | response = "Error with request"}


-- SUBSCRIPTIONS

subscriptions : Model -> Sub Msg
subscriptions model =
  Sub.none


-- VIEW

view : Model -> Html Msg
view model =
  div []
    [ input [ placeholder "Type equation here.", onInput Change ] []
    , button [ onClick Submit ] [ text "Submit" ]
    , div [] [ text model.response ]
    ]
