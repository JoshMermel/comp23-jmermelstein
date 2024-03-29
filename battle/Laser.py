import pygame, os, sys
from pygame.locals import *
from random import randint

class Laser(pygame.sprite.Sprite):
    ''' A simple sprite that moves vertically'''
    
    def load_image(self, image_name):
        ''' The proper way to load an image '''
        try:
            image = pygame.image.load(image_name)
        except pygame.error, message:
            print "Cannot load image: " + image_name
            raise SystemExit, message
        return image.convert_alpha()

    def __init__(self, screen, init_x, init_y, init_y_speed):
        ''' Create the LaserBolt at (x, y) moving up at a given speed '''
        pygame.sprite.Sprite.__init__(self) #call Sprite intializer
        
        # Load the image
        self.image = self.load_image('assets/assets/laser.gif')

        self.screen = screen

        # Create a moving collision box
        self.rect = self.image.get_rect()
        self.image_w = self.rect[2]
        self.image_h = self.rect[3]
        self.x = init_x
        self.y = init_y
        self.rect.x = init_x
        self.rect.y = init_y
                
        # Set the default speed (dx, dy)
        self.dy = init_y_speed

        # what does this even do?
        self.active = True
                
    def update(self):
        ''' Move the sprite '''
        self.y += self.dy
        self.rect.y += self.dy
        
        # Remove sprite from group if it goes off the screen...
        if self.rect.y <= 0:
            self.kill() # see http://pygame.org/docs/ref/sprite.html#Sprite.kill

    def draw(self):
        '''draw the sprite'''
        self.screen.blit(self.image, (self.x, self.y))
                    
if __name__ == "__main__":
    # Check if sound and font are supported
    if not pygame.font:
        print "Warning, fonts disabled"
    if not pygame.mixer:
        print "Warning, sound disabled"
        
    # Constants
    FPS = 50
    SCREEN_WIDTH, SCREEN_HEIGHT = 800, 600
    BACKGROUND_COLOR = (0, 0, 0)
    
    # Initialize Pygame, the clock (for FPS), and a simple counter
    pygame.init()
    pygame.display.set_caption('Laser')
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT), 0, 32)
    clock = pygame.time.Clock()

    # Create the sprite group
    lasers = pygame.sprite.Group()

    time_passed = 0
    # Game loop
    while True:
        time_passed = clock.tick(FPS)
        
        # Add a new laser at random x-coordinate with random speed
        lasers.add(Laser(screen, randint(1, SCREEN_WIDTH), 550, randint(1, 10) * -1))
        
        # Event handling here (to quit)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    pygame.quit()
                    sys.exit()                  
        
        # update and redraw
        screen.fill(BACKGROUND_COLOR)
        lasers.update()
        for l in lasers:
            l.draw()
        pygame.display.update()
