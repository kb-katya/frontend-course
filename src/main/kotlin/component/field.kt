package component

import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*

interface FieldProps : RProps {
    var label: String
    var value: String
    var placeholder: String
    var type: InputType
    var onChange: (Event) -> Unit
}

val fField =
    functionalComponent<FieldProps> { props ->
        label {
            +props.label
        }
        input {
            attrs.value = props.value
            attrs.onChangeFunction = props.onChange
            attrs.placeholder = props.placeholder
            attrs.type = props.type
        }
    }

fun RBuilder.field(
    label: String,
    value: String,
    placeholder: String,
    type: InputType,
    onChange: (Event) -> Unit
) = child(fField) {
    attrs.label = label
    attrs.value = value
    attrs.placeholder = placeholder
    attrs.type = type
    attrs.onChange = onChange
}