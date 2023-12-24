import time
import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By


cService = webdriver.ChromeService(executable_path='Facts Scraper\\chromedriver.exe')
driver = webdriver.Chrome(service = cService)

path = "PDF Scraper\\data"
codenames = []
codes = []
temp = []
with open(path) as file:
    for line in file:
            temp.append(line)
    for i in temp:
        codenames.append(i.split("'")[3])
        codes.append(i.split("'")[1][-2:])
print(codenames)
print(codes)
text = []
for n, i in enumerate(codenames):
    try:
        i.replace(" ", "+")
        driver.get(f"https://www.google.com/search?q=interesting+facts+about+{i}")
        s = driver.find_element(By.CLASS_NAME, "hgKElc").text + codes[n]
        s.replace("\n", "")
        text.append(s)
        time.sleep(1)
    except selenium.common.exceptions.NoSuchElementException:
        text.append(" ")
        continue

with open("Facts Scraper\\facts.txt", 'w', encoding="utf8") as fx:
    fx.write("\n".join(text))
