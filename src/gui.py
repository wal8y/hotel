import tkinter as tk

def button_click():
    return 1

root = tk.Tk()
button = tk.Button(root, text="NY", command=button_click)
button1 = tk.Button(root, text="lon", command=button_click)
button2 = tk.Button(root, text="ist", command=button_click)
button3 = tk.Button(root, text="tok", command=button_click)
button.pack()
