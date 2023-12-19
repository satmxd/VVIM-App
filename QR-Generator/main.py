import customtkinter as ctk
import tkinter as tk
from tkinter import Frame, Label
import qrcode
from PIL import Image, ImageTk
from datetime import datetime

ctk.set_appearance_mode("dark")       
ctk.set_default_color_theme("dark-blue")   


class App(ctk.CTk):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.title("QR Code GUI")   
        self.geometry("650x350")
        self.save_path = 'QR-Generator\\bgpy.jpg'
        self.boxsize = 20
        self.border = 1
        self.data = 'test'
        self.filename = 'test'

        self.boxsizelabel = ctk.CTkLabel(self,
                                      text="Box Size")
        self.boxsizelabel.grid(row=0, column=0,
                            padx=20, pady=20,
                            sticky="ww")

        self.boxsizeentry = ctk.CTkEntry(self,
                         placeholder_text="Enter Size")
        self.boxsizeentry.grid(row=0, column=1,
                            columnspan=1, padx=20,
                            pady=20, sticky="ww")


        self.bordersizelabel = ctk.CTkLabel(self,
                                      text="Border Size")
        self.bordersizelabel.grid(row=1, column=0,
                            padx=20, pady=20,
                            sticky="ww")
        self.bordersizeentry = ctk.CTkEntry(self,
                         placeholder_text="Enter Size")
        self.bordersizeentry.grid(row=1, column=1,
                            columnspan=1, padx=20,
                            pady=20, sticky="ww")

        self.textinputlabel = ctk.CTkLabel(self,
                                      text="Text Input")
        self.textinputlabel.grid(row=2, column=0,
                            padx=20, pady=20,
                            sticky="ww")
        self.textinputentry = ctk.CTkEntry(self,
                         placeholder_text="Enter Data")
        self.textinputentry.grid(row=2, column=1,
                            columnspan=1, padx=20,
                            pady=20, sticky="ww")

        self.embedimg = ctk.CTkLabel(self,
                                  text="Embed Logo")
        self.embedimg.grid(row=3, column=0,
                              padx=20, pady=20,
                              sticky="ww")

        self.embedimgvar = tk.BooleanVar(value=False)
         
        self.embedimgbox = ctk.CTkCheckBox(self,
                             text="Yes",
                             variable=self.embedimgvar,
                             onvalue=True, offvalue=False)
        self.embedimgbox.grid(row=3, column=1,
                          padx=20, pady=20,
                          sticky="ew")
        
        self.generateqrcodebutton = ctk.CTkButton(self,
                                         text="Generate QR Code",
                                         command=self.make_qr)
        self.generateqrcodebutton.grid(row=4, column=1,
                                        columnspan = 1,
                                        padx=20,
                                        pady=20, sticky="ew")
    
        self.filenameentry = ctk.CTkEntry(self,
                         placeholder_text="Enter Filename")
        self.filenameentry.grid(row=4, column=0,
                            columnspan=1, padx=20,
                            pady=20, sticky="ew")


        # self.saveqrbutton = ctk.CTkButton(self,
        #                                  text="Save QR Code",
        #                                  command=self.save_qr)
        # self.saveqrbutton.grid(row=4, column=1,
        #                                 columnspan=2, padx=20,
        #                                 pady=20, sticky="ew")

        self.show_qr_button = ctk.CTkButton(self,
                                         text="Show QR Code",
                                         command=self.show_qr)
        self.show_qr_button.grid(row=4, column=2,
                                        columnspan=2, padx=20,
                                        pady=20, sticky="ew")
        self.qr_image = ctk.CTkImage(Image.open(self.save_path), size=(200, 200))

        self.qr_image_label = ctk.CTkLabel(self, text="", image=self.qr_image)
        self.qr_image_label.place(relx=0.61, rely=0.69, anchor=ctk.SW)




    def make_qr(self):#, boxsize, border, data, embed_img = True):
        self.boxsize = self.boxsizeentry.get() if self.boxsizeentry.get() != '' else 50
        self.border = self.bordersizeentry.get() if self.bordersizeentry.get() != '' else 2
        self.data = self.textinputentry.get()


        def prep_image():
            logo_link = 'QR-Generator\\py.jpg'
            logo = Image.open(logo_link)
            width = 150
            wpercent = (width/float(logo.size[0]))
            hsize = int((float(logo.size[1])*float(wpercent)))
            logo = logo.resize((width, hsize), Image.ANTIALIAS)
            return logo

        self.qr = qrcode.QRCode(error_correction=qrcode.ERROR_CORRECT_H
        ,box_size=self.boxsize, border= self.border)

        self.qr.add_data(self.data)
        self.qr.make()

        self.img = self.qr.make_image()

        self.logo = prep_image()

        pos = ((self.img.size[0] - self.logo.size[0]) // 2,
        (self.img.size[1] - self.logo.size[1]) // 2)

        print(self.embedimgvar.get())
        if self.embedimgvar.get():
            self.img.paste(self.logo, pos)
        
        self.filename = self.filenameentry.get()
        date = datetime.now().strftime("%Y-%m-%d_%I-%M-%Spm")
        self.filename += str(date)
        self.save_path = ("QR-Generator\\codes\\{0}.{1}".format(self.filename, 'png'))
        self.img.save(self.save_path)
        print(self.save_path)

    def show_qr(self):
        self.qr_image = ctk.CTkImage(Image.open(self.save_path), size=(200, 200))

        self.qr_image_label = ctk.CTkLabel(self, text="", image=self.qr_image)
        self.qr_image_label.place(relx=0.61, rely=0.69, anchor=ctk.SW)


if __name__ == "__main__":
    app = App()
    app.mainloop() 