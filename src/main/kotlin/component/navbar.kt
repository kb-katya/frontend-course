package component

import data.NavItem
import kotlinx.html.id
import react.*
import react.dom.*
import react.router.dom.navLink

interface NavbarProps : RProps {
    var navItemList: List<NavItem>
}

val fNavbar =
    functionalComponent<NavbarProps> { props ->
        nav {
            attrs.id = "navbar"
            img {
                attrs.src = "./images/logo.png"
            }
            props.navItemList.map {
                div {
                    navLink(it.path) { +it.title }
                }
            }
        }
    }

fun RBuilder.navbar(
    navItemList: List<NavItem>
) = child(fNavbar) {
    attrs.navItemList = navItemList
}