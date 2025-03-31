const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');

registerBtn.addEventListener('click', () => {
    container.classList.add('active');
});

loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
});

// Mostrar/ocultar contraseña - login
const togglePassword = document.getElementById('togglePassword');
const passwordInput = document.getElementById('password');

togglePassword.addEventListener('click', () => {
    const isPassword = passwordInput.getAttribute('type') === 'password';
    passwordInput.setAttribute('type', isPassword ? 'text' : 'password');
    togglePassword.classList.toggle('bx-show');
    togglePassword.classList.toggle('bx-hide');
    togglePassword.setAttribute('title', isPassword ? 'Ocultar contraseña' : 'Mostrar contraseña');
});

// Mostrar/ocultar contraseña - nuevo password
const toggleRegisterPassword = document.getElementById('toggleRegisterPassword');
const registerPasswordInput = document.getElementById('registerPassword');

toggleRegisterPassword.addEventListener('click', () => {
    const isPassword = registerPasswordInput.getAttribute('type') === 'password';
    registerPasswordInput.setAttribute('type', isPassword ? 'text' : 'password');
    toggleRegisterPassword.classList.toggle('bx-show');
    toggleRegisterPassword.classList.toggle('bx-hide');
    toggleRegisterPassword.setAttribute('title', isPassword ? 'Ocultar contraseña' : 'Mostrar contraseña');
});

// Mostrar/ocultar contraseña - confirmar password
const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');
const confirmPasswordInput = document.getElementById('confirmPassword');

toggleConfirmPassword.addEventListener('click', () => {
    const isPassword = confirmPasswordInput.getAttribute('type') === 'password';
    confirmPasswordInput.setAttribute('type', isPassword ? 'text' : 'password');
    toggleConfirmPassword.classList.toggle('bx-show');
    toggleConfirmPassword.classList.toggle('bx-hide');
    toggleConfirmPassword.setAttribute('title', isPassword ? 'Ocultar contraseña' : 'Mostrar contraseña');
});
