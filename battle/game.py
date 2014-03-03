import pygame, sys, os
from Laser import Laser
from Battlecruiser import Battlecruiser
from Enemy import Enemy
from pygame.locals import * 
from pygame.key import *
from random import randint

# Initialize all imported Pygame modules (a.k.a., get things started)
pygame.init()
clock = pygame.time.Clock()

# Constants
FPS = 50
SCREEN_WIDTH, SCREEN_HEIGHT = 800, 600
LASER_SPEED = -5
MAX_SPEED = 20 #in x and y directions separatly, not normalized
num_enemies = 5
gameover = False

# Set the display's dimensions
screenDimensions = (SCREEN_WIDTH, SCREEN_HEIGHT)
window = pygame.display.set_mode(screenDimensions)
pygame.display.set_caption('Battle for Ram Aras') # Set the window bar title
screen = pygame.display.get_surface() # This is where images are displayed

my_cruiser = Battlecruiser(screen, SCREEN_WIDTH/2, SCREEN_HEIGHT/2)
my_cruiser.draw()
font = pygame.font.Font(None, 28)
score = 0
COUNTER_LOCATION = (10, 10)

def input(events):
    ''' Function to handle events, particularly quitting the program '''
    for event in events:
        if event.type == QUIT:
            quit()
        elif event.type == KEYDOWN:
            if event.key == K_ESCAPE:
                quit()
            if event.key == K_SPACE:
                my_cruiser.create_laser()

enemies = pygame.sprite.Group()
for i in range(num_enemies):
    enemies.add(Enemy(screen, randint(1, SCREEN_WIDTH), 0, randint(1, 5), randint(1,5)))
back1_x = 0
gameover_text = font.render('GAMEOVER',0, (255,255,255))

# background
try:
    background = pygame.image.load('assets/assets/ram_aras.png').convert()
except pygame.error, message:
    print "Cannot load image: " + image_name
    raise SystemExit, message
background =  background.convert()
background_pos = 0
time_to_respawn = 0

# The game loop
while True:
    input(pygame.event.get())
    screen.blit(background, (0,background_pos))
    background_pos -= 1
    if background_pos <= SCREEN_HEIGHT - background.get_height():
        background_pos = 0
    text = font.render("Score:" + str(score), 1, (0,255,0))
    time_passed = clock.tick(FPS)
    my_cruiser.update()
    my_cruiser.draw()
    screen.blit(text, COUNTER_LOCATION)
    enemies.update()
    time_to_respawn += 1
    if time_to_respawn >= 100 and not gameover:
        enemies.add(Enemy(screen, randint(1, SCREEN_WIDTH), 0, randint(1, 5), randint(1,5)))
        enemies.add(Enemy(screen, randint(1, SCREEN_WIDTH), 0, randint(1, 5), randint(1,5)))
        enemies.add(Enemy(screen, randint(1, SCREEN_WIDTH), 0, randint(1, 5), randint(1,5)))
        time_to_respawn = 0
    for e in enemies:
        e.draw()
        if pygame.sprite.spritecollide(e, my_cruiser.lasers, True):
            score += e.die()
    if pygame.sprite.spritecollide(my_cruiser, enemies, False):
        my_cruiser.die()
        gameover = True
    if gameover:
        screen.blit(gameover_text, (SCREEN_WIDTH/2, SCREEN_HEIGHT/2))
    pygame.display.flip()
