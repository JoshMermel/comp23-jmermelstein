import pygame, sys, os
from Laser import Laser
from pygame.locals import * 
from pygame.key import *

# Constants
FPS = 50
SCREEN_WIDTH, SCREEN_HEIGHT = 800, 600
BACKGROUND_COLOR = (0, 0, 0)
LASER_SPEED = -5
MAX_SPEED = 20 #in x and y directions separatly, not normalized

class Battlecruiser(pygame.sprite.Sprite):
    '''A player controlled military spacecraft'''

    def load_image(self, image_name):
        ''' The proper way to load an image '''
        try:
            image = pygame.image.load(image_name).convert()
        except pygame.error, message:
            print "Cannot load image: " + image_name
            raise SystemExit, message
        return image.convert_alpha()

    def load_sound(self, sound_name):
        ''' The proper way to load a sound '''
        try:
            sound = pygame.mixer.Sound(sound_name)
        except pygame.error, message:
            print "Cannot load sound: " + sound_name
            raise SystemExit, message
        return sound

    def __init__(self, screen, init_x, init_y):
        ''' Create the LaserBolt at (x, y) moving up at a given speed '''
        pygame.sprite.Sprite.__init__(self) #call Sprite intializer

        # Load the image
        self.image = self.load_image('assets/assets/battlecruiser.gif')

        # Load sounds
        self.death_sound = self.load_sound('assets/assets/death_explode.wav')
        self.fire_sound = self.load_sound('assets/assets/laser.wav')
        self.screen = screen

        # Create a moving collision box
        self.rect = self.image.get_rect()
        self.image_w = self.rect[2]
        self.image_h = self.rect[3]
        self.x = init_x
        self.rect.x = init_x
        self.y = init_y
        self.rect.y = init_y

        # Initialize movement
        self.dx = 0
        self.dy = 0

        # Initialize group of lasers
        self.lasers = pygame.sprite.Group()

        # what does this even do?
        self.active = True

    def create_laser(self):
        self.fire_sound.play()
        self.lasers.add(Laser(self.screen, (self.x + self.image_w/2), self.y, LASER_SPEED))

    def update(self):
        ''' Move the sprite '''
        if self.active:
            keys = pygame.key.get_pressed()
            if keys[K_LEFT]:
                self.dx -= 2
            if keys[K_RIGHT]:
                self.dx += 2
            if keys[K_UP]:
                self.dy -= 2
            if keys[K_DOWN]:
                self.dy += 2
            if keys[K_s]:
                self.create_laser()

            if abs(self.dx) > MAX_SPEED:
                self.dx = MAX_SPEED * abs(self.dx)/self.dx
            if abs(self.dy) > MAX_SPEED:
                self.dy = MAX_SPEED * abs(self.dy)/self.dy

            self.x += self.dx
            self.y += self.dy
            self.rect.x = self.rect.x + self.dx
            self.rect.y = self.rect.y + self.dy
            # Slow to a stop
            if self.dx != 0:
                self.dx -= abs(self.dx)/self.dx
            if self.dy != 0:
                self.dy -= abs(self.dy)/self.dy
            self.rect.x %= SCREEN_WIDTH
            self.x %= SCREEN_WIDTH
            self.rect.y %= SCREEN_HEIGHT
            self.y %= SCREEN_HEIGHT
        self.lasers.update()

    def draw(self):
        if self.active:
            self.screen.blit(self.image, (self.x, self.y))
            for l in self.lasers:
                l.draw()

    def die(self):
        if self.active:
            self.active = False
            self.death_sound.play()
            self.kill()




def quit():
    ''' Self explanatory '''
    pygame.quit()
    sys.exit(0)

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


if __name__ == "__main__":
    # Initialize all imported Pygame modules (a.k.a., get things started)
    pygame.init()
    clock = pygame.time.Clock()


    # Set the display's dimensions
    screenDimensions = (SCREEN_WIDTH, SCREEN_HEIGHT)
    window = pygame.display.set_mode(screenDimensions)
    pygame.display.set_caption('Battlecruiser') # Set the window bar title
    screen = pygame.display.get_surface() # This is where images are displayed

    # Clear the background
    background = pygame.Surface(screen.get_size()).convert()
    background.fill(BACKGROUND_COLOR)
    screen.blit(background, (0,0))

    my_cruiser = Battlecruiser(screen, 100,100)
    my_cruiser.draw()
    score = 0
    COUNTER_LOCATION = (10, 10)

    # The game loop
    while True:
        time_passed = clock.tick(FPS)
        screen.fill((0,0,0))
        input(pygame.event.get())
        my_cruiser.update()
        my_cruiser.draw()
        pygame.display.flip()
