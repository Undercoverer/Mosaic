package gay.extremist.mosaic.components.widgets

/**
val SearchBarInput by InputGroupStyle.addVariantBase {
    Modifier
        .setVariable(InputVars.BorderColor, Colors.Transparent)
        .setVariable(ColorVar, colorMode.toPalette().background)
        .backgroundColor(Color.rgba(220, 233, 250, 0.2f).darkened(0.1f))
}

@Composable
fun SearchForm(viewModel: DataPageVM.SearchBarVM) {
    Form(
        attrs = DarkBackgroundBoxStyle.toModifier()
            .fillMaxWidth()
            .margin(topBottom = 0.5.cssRem)
            .textAlign(TextAlign.Center)
            .toAttrs {
                onSubmit {
                    it.preventDefault() // This stops the form from "submitting"
                    viewModel.onEnterSearch()
                }
            }
    ) {
        val dataListId = "search-list"
        key(viewModel.suggestions) {
            Datalist(Modifier.id(dataListId).toAttrs()) {
                viewModel.suggestions.forEach {
                    Option(it) // tried using key() but it didn't seem to help
                }
            }
        }
        SearchBar(dataListId, viewModel)
        Text("Search and select an instructor or subject, or enter a course code")
    }
}

@Composable
private fun SearchBar(dataListId: String, viewModel: DataPageVM.SearchBarVM) {
    InputGroup(Modifier.margin(bottom = 0.25.cssRem), variant = SearchBarInput) {
        TextInput(
            viewModel.input,
            { viewModel.input = viewModel.inputTransform(it) },
            Modifier
                .onClick { viewModel.active = true }
                .attrsModifier { attr("list", dataListId) },
            placeholder = viewModel.placeholder,
            focusBorderColor = Colors.Transparent,
        )
        RightInset {
            Button(
                onClick = {},
                modifier = Modifier.ariaLabel("Search"),
                variant = UnstyledButtonVariant,
                size = UnsetButtonSize,
                type = ButtonType.Submit,
            ) {
                FaMagnifyingGlass()
            }
        }
    }
}
**/