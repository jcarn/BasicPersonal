def censor(text, word):
    ast_word = ""
    censor_word = []
    for c in word:
        ast_word += "*"
    for w in text.split(" "):
        if w in word:
            censor_word.append(ast_word)
        else:
            censor_word.append(w)
    return " ".join(censor_word)

print censor("yo my name is yo", "yo")