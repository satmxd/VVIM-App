import qrcode
from PIL import Image
from datetime import datetime

# boxsize = 2
# border_width = 2
# data = "Test"

class QRCodeGen:
    def make_qr(self, boxsize, border, data, embed_img = True):

        def prep_image():
            logo_link = 'py.jpg'
            logo = Image.open(logo_link)
            width = 150
            wpercent = (width/float(logo.size[0]))
            hsize = int((float(logo.size[1])*float(wpercent)))
            logo = logo.resize((width, hsize), Image.ANTIALIAS)
            return logo

        self.qr = qrcode.QRCode(error_correction=qrcode.ERROR_CORRECT_H
        ,box_size=boxsize, border= border)

        self.qr.add_data(data)
        self.qr.make()

        self.img = self.qr.make_image()

        self.logo = prep_image()

        pos = ((self.img.size[0] - self.logo.size[0]) // 2,
        (self.img.size[1] - self.logo.size[1]) // 2)

        if embed_img:
            self.img.paste(self.logo, pos)
    
    def save(self, filename, showimg = True):
        date = datetime.now().strftime("%Y-%m-%d_%I-%M-%Spm")
        filename += str(date)
        self.save_path = ("codes/{0}.{1}".format(filename, 'png'))
        self.img.save(self.save_path)
        print(self.save_path)
            

        if showimg:
            self.img.show()
        


qrgen = QRCodeGen()
qrgen.make_qr(20, 2, "Hello World", True)
qrgen.save("test")

