from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import ElementNotInteractableException, TimeoutException, NoSuchElementException
from selenium.webdriver.remote.webelement import WebElement
import time
import os
from dotenv import load_dotenv

load_dotenv()

element_identifiers = {
    "translations_area_element": '/html/body/c-wiz/div/div[2]/c-wiz/div[2]',
    "language_drop_down_element": '/html/body/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[1]/c-wiz/div[5]/button/span',
    "language_table_element": '//*[@id="yDmH0d"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[3]/c-wiz/div[2]/div/div[3]/div',
    "hindi_language_option_element": '/html/body/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[3]/c-wiz/div[2]/div/div[3]/div/div[2]/div[39]',
    "english_text_area_element": '//*[@id="yDmH0d"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[2]/c-wiz[1]/span/span/div/textarea',
    "translated_text_area_element": '/html/body/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[2]/c-wiz[2]/div[5]/div/div[1]/span[1]/span/span',
    "english_translation_area_element": '//*[@id="yDmH0d"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[2]/c-wiz/section/div/div/div[1]/div[1]',
    "hindi_translation_area_element": "J0lOec",
    "type_of_usage_element": "KWoJId",
    "english_translation_and_example_element": "eqNifb",
    "english_translation_element": "fw3eif",
    "english_example_element": "MZgjEb",
    "numbering_element": "RSggmb"
}


class Translator:

    PATH = os.getenv("CHROMEDRIVER_PATH")
    BASE_URL = "https://translate.google.co.in/?sl=en&tl=hi&text={}&op=translate"
    TIMEOUT = int(os.getenv("SELENIUM_TIME_OUT"))

    def __init__(self):
        self.driver = self.__initialise_driver()

    def __initialise_driver(self):
        chrome_options = webdriver.ChromeOptions()
        chrome_options.add_argument("--headless")
        chrome_options.add_argument("--disable-gpu")
        chrome_options.add_argument("--no-sandbox")
        chrome_options.binary_location = os.getenv("GOOGLE_CHROME_PATH")


        driver = webdriver.Chrome(executable_path=Translator.PATH, chrome_options=chrome_options)
        return driver

    @staticmethod
    def __get_selenium_element(element, element_name, by, timeout=TIMEOUT):
        try:
            if by == "xpath":
                selenium_element = WebDriverWait(element, timeout).until(
                    EC.presence_of_element_located((By.XPATH, element_name))
                )
                return selenium_element
            elif by == "class_name":
                selenium_element = WebDriverWait(element, timeout).until(
                    EC.presence_of_element_located((By.CLASS_NAME, element_name))
                )
                return selenium_element
            else:
                pass
        except TimeoutException:
            return None

    @staticmethod
    def __get_selenium_elements(element, element_name, by, timeout=TIMEOUT):
        try:
            if by == "xpath":
                selenium_element = WebDriverWait(element, timeout).until(
                    EC.presence_of_all_elements_located((By.XPATH, element_name))
                )
                return selenium_element
            elif by == "class_name":
                selenium_element = WebDriverWait(element, timeout).until(
                    EC.presence_of_all_elements_located((By.CLASS_NAME, element_name))
                )
                return selenium_element
            else:
                pass
        except TimeoutException:
            return None
        except AttributeError:
            return None

    @staticmethod
    def __organise_translation_data(usages, translations, examples, numberings):
        if translations.count("") == examples.count(""):
            translations = [item for item in translations if item is not ""]
            examples = [item for item in examples if item is not ""]
            usages = [item for item in usages if item is not ""]
            numberings = [item for item in numberings if item is not ""]

        numberings = [int(x) for x in numberings]
        translations = [item.strip("\n. ") for item in translations]
        examples = [item.strip("\n. ") for item in examples]
        usages = [item.strip("\n. ") for item in usages]

        breaks = [i for i in range(0, len(numberings) - 1) if numberings[i] >= numberings[i+1]]
        print(f"breaks: {breaks}")

        translation_data = []
        if len(breaks) == 0:
            single_usage = {
                "english_translations": translations,
                "english_examples": examples
            }
            translation_data.append(single_usage)
        else:
            for j in range(0, len(breaks)):
                single_usage = {}
                left = 0 if j == 0 else breaks[j-1] + 1
                right = breaks[j] + 1
                print(f"left = {left}, right = {right}")
                single_usage["english_translations"] = translations[left: right]
                single_usage["english_examples"] = examples[left: right]
                translation_data.append(single_usage)
                if j == len(breaks) - 1:
                    single_usage = {}
                    single_usage["english_translations"] = translations[right:]
                    single_usage["english_examples"] = examples[right:]
                    translation_data.append(single_usage)

        for i in range(len(usages)):
            translation_data[i]["usage"] = usages[i]

        return translation_data

    def __get_required_selemium_elements(self, word):
        self.driver.get(Translator.BASE_URL.format(word))

        print("Looking for translations area element.")
        translations_area = self.__get_selenium_element(
            self.driver,
            element_identifiers.get("translations_area_element"),
            "xpath"
        )

        print("Looking for hindi translation area element.")
        hindi_translation_area = self.__get_selenium_element(
            translations_area,
            element_identifiers.get("hindi_translation_area_element"),
            "class_name"
        )

        print("Looking for english translation area element")
        english_translation_area = self.__get_selenium_element(
            translations_area,
            element_identifiers.get("english_translation_area_element"),
            "xpath"
        )

        print("Looking for usages element.")
        usages = self.__get_selenium_elements(
            english_translation_area,
            element_identifiers.get("type_of_usage_element"),
            "class_name",
            1
        )

        print("Looking for english translation and example area.")
        e_translation_and_example = self.__get_selenium_elements(
            english_translation_area,
            element_identifiers.get("english_translation_and_example_element"),
            "class_name",
            1
        )

        elements_dictionary = {
            "hindi_translation_area": hindi_translation_area,
            "usages": usages,
            "e_translation_and_example": e_translation_and_example
        }
        return elements_dictionary

    def __verify_if_all_elements_exist(self, elements_dict):
        elements = list(elements_dict.values())
        for element in elements:
            if not element:
                return False
        return True

    def __scrape_translation(self, elements_dict):
        hindi_translation = elements_dict.get("hindi_translation_area").text.strip("\n ")

        usages_element = elements_dict.get("usages")
        usages = [item.text.title() for item in usages_element if item.text is not ""]

        translations = []
        examples =  []
        numberings = []
        for item in elements_dict.get("e_translation_and_example"):
            translation = item.find_element_by_class_name(element_identifiers.get("english_translation_element")).text
            if translation is not "":
                translations.append(translation)
            else: 
                pass

            try:
                example = item.find_element_by_class_name(element_identifiers.get("english_example_element")).text
                if example is not "":
                    examples.append(example)
                else:
                    pass
            except NoSuchElementException:
                examples.append("")

            number = item.find_element_by_class_name(element_identifiers.get("numbering_element")).text
            if number is not "":
                numberings.append(number)
            else:
                pass

        return hindi_translation, usages, translations, examples, numberings

    def get_translation(self, word: str) -> dict:
        elements = self.__get_required_selemium_elements(word)
        if self.__verify_if_all_elements_exist(elements):
            hindi_translation, usages, translations, examples, numberings = self.__scrape_translation(elements)
            translation_data = self.__organise_translation_data(usages, translations, examples, numberings)
            final_translation_data = {
                "hindi": hindi_translation,
                "english": translation_data
            }
            return final_translation_data
        else:
            empty_response = {
                "hindi": elements.get("hindi_translation_area").text.strip("\n. "),
                "english": [
                    {
                        "english_translation": [],
                        "english_examples": [],
                        "usage": ""
                    }
                ]
            }
            return empty_response

    def teardown(self):
        self.driver.quit()


if __name__ == "__main__":
    translator = Translator()
    print(translator.get_translation("sane"))
    translator.teardown()

