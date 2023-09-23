import requests

def RecipeGeneration(Ingredients):
    response = requests.post("https://bilalsardar-like-chatgpt-clone.hf.space/run/predict", json={
      "data": [
        "your are a chef who take ingredients and tell a recipe name and that whole recipe that we can make with those ingredients ",
        "YOUR OPENAI API KEY",
        Ingredients,
    ]}).json()

    data = response["data"]

    return data[0]
