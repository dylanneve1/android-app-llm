import pygame

# Initialize the game
pygame.init()

# Set up the game window
screen = pygame.display.set_mode((600, 400))

# Main game loop
running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

# Quit the game
pygame.quit()
