export interface User {
  id?: number; // Campo opcional para el ID del usuario.
  email: string; // Campo para el correo electrónico del usuario.
  password: string; // Campo para la contraseña del usuario.
  accountType: 'professional' | 'particular'; // Campo para el tipo de cuenta.
  isEnabled?: boolean; // Campo opcional para indicar si la cuenta está habilitada.
}
