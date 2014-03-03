import pygame, os, sys
from pygame.locals import *
from random import randint
        
# Constants
FPS = 50
SCREEN_WIDTH, SCREEN_HEIGHT = 800, 600
BACKGROUND_COLOR = (255, 255, 255)
num_enemies = 10

class Enemy(pygame.sprite.Sprite):
    ''' A simple enemy that bounces off the walls '''
    
    def load_image(self, image_name):
        ''' The proper way to load an image '''
        try:
            image = pygame.image.load(image_name)
        except pygame.error, message:
            print "Cannot load image: " + image_name
            raise SystemExit, message
        return image.convert_alpha()



    def __init__(self, screen, init_x, init_y, init_y_speed, init_x_speed):
        ''' Create the LaserBolt at (x, y) moving up at a given speed '''
        pygame.sprite.Sprite.__init__(self) #call Sprite intializer
        
        # Load the image
        self.image = self.load_image('assets/assets/mutalisk.gif')
        self.death_image = self.load_image('assets/assets/laser_explosion.gif')

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
        self.dx = init_x_speed
        self.dy = init_y_speed

        self.active = True
        self.time_left = FPS/2 # time until we remove the death image
                
    def update(self):
        ''' Move the sprite '''
        if ((self.x + self.dx) <= 0):
            self.dx = self.dx * -1
        if ((self.x + self.dx) >= self.screen.get_size()[0]):
            self.dx = self.dx * -1
        if ((self.y + self.dy) <= 0):
            self.dy = self.dy * -1
        if ((self.y + self.dy) >= self.screen.get_size()[1]):
            self.dy = self.dy * -1

        self.x += self.dx
        self.rect.x += self.dx
        self.y += self.dy
        self.rect.y += self.dy
        
        # Remove sprite from group if it goes off the screen...
        if self.rect.y <= 0:
            self.kill() # see http://pygame.org/docs/ref/sprite.html#Sprite.kill

    def draw(self):
        if self.active:
            self.screen.blit(self.image, (self.x, self.y))
        else:
            self.dx = 0
            self.dy = 0
            self.screen.blit(self.death_image, (self.x, self.y))
            self.time_left -= 1

        if self.time_left <= 0:
            self.kill()
            
    def die(self):
        if not self.active:
            return 0
        self.active = False
        return 100
        
                    
if __name__ == "__main__":
    # Check if sound and font are supported
    if not pygame.font:
        print "Warning, fonts disabled"
    if not pygame.mixer:
        print "Warning, sound disabled"

    
    # Initialize Pygame, the clock (for FPS), and a simple counter
    pygame.init()
    pygame.display.set_caption('Enemy.py')
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT), 0, 32)
    clock = pygame.time.Clock()

    # Create the sprite group
    enemies = pygame.sprite.Group()

    # Add a new laser at random x-coordinate with random speed
    for i in range(num_enemies):
        enemies.add(Enemy(screen, randint(1, SCREEN_WIDTH), 0, randint(1, 5), randint(1,5)))
    # Game loop
    while True:
        time_passed = clock.tick(FPS)
        
        # Event handling here (to quit)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    pygame.quit()
                    sys.exit()                  
                if event.key == K_SPACE:
                    for l in enemies:
                        l.die()
        
        # Redraw the background
        screen.fill(BACKGROUND_COLOR)
        
        # Update and redraw all sprites
        enemies.update()
        for l in enemies:
            l.draw()
        
        # Draw the sprites
        pygame.display.update()
